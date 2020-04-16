import random
import other_bots
import traders
import run_experiments
import plot_simulation
import numpy
import math

class MyBot(traders.Trader):
    name = 'my_bot'

    def simulation_params(self, timesteps,
                          possible_jump_locations,
                          single_jump_probability):
        """Receive information about the simulation."""
        # Number of trading opportunities
        self.timesteps = timesteps
        # A list of timesteps when there could be a jump
        self.possible_jump_locations = possible_jump_locations
        # For each of the possible jump locations, the probability of
        # actually jumping at that point. Jumps are normally
        # distributed with mean 0 and standard deviation 0.2.
        self.single_jump_probability = single_jump_probability
        # A place to store the information we get
        self.information = []

        # A place to store our expected probability
        self.belief = 0.5
        self.market_belief = 0.5
        self.market_beliefs = []

        self.max_position = 120
        self.min_position = -1 * self.max_position 
        self.max_trade = 60
        self.min_gap_to_trade = 0.2

        self.memory = 5
        self.use_full_info_history = True
        self.pct_info = .20
        self.pct_mkt = .80

        #which is used depends on movement of market_belief from last period to current, normal 0.5, 0.5
        self.pct_bridge = 0.5
        self.pct_bridge_reflexive = 0.5

    def new_information(self, info, time):
        """Get information about the underlying market value.
        
        info: 1 with probability equal to the current
          underlying market value, and 0 otherwise.
        time: The current timestep for the experiment. It
          matches up with possible_jump_locations. It will
          be between 0 and self.timesteps - 1."""
        self.information.append(info)

    def trades_history(self, trades, time):
        """A list of everyone's trades, in the following format:
        [(execution_price, 'buy' or 'sell', quantity,
          previous_market_belief), ...]
        Note that this isn't just new trades; it's all of them."""
        self.trades = trades

    def find_qty_buy(self, check_callback, pct_bridge, target):
        if self.market_belief >= target:
            return 0

        qty = 1
        partial_target = self.market_belief - ((1-pct_bridge) * (target - self.market_belief))
        while(check_callback('buy', qty + 1) < target):
            qty+=1
        return qty

    def find_qty_sell(self, check_callback, pct_bridge, target):
        if self.market_belief <= target:
            return 0

        qty = 1
        partial_target = self.market_belief + ((1-pct_bridge) * (self.market_belief - target))
        while(check_callback('sell', qty + 1) > target):
            qty+=1
        return qty

    def trading_opportunity(self, cash_callback, shares_callback,
                            check_callback, execute_callback,
                            market_belief):
        """Called when the bot has an opportunity to trade.
        
        cash_callback(): How much cash the bot has right now.
        shares_callback(): How many shares the bot owns.
        check_callback(buysell, quantity): Returns the per-share
          price of buying or selling the given quantity.
        execute_callback(buysell, quantity): Buy or sell the given
          quantity of shares.
        market_belief: The market maker's current belief.

        Note that a bot can always buy and sell: the bot will borrow
        shares or cash automatically.
        """
        
        self.market_belief = market_belief
        self.market_beliefs.append(market_belief)


        time = len(self.information)
        position = shares_callback()
        if time > self.memory:
            price_dropped = self.market_beliefs[-2] - market_belief > 0

            info_contribution = self.information if self.use_full_info_history else self.information[time - self.memory:]
            self.belief = (self.pct_info * 100 * numpy.mean(info_contribution) 
                + self.pct_mkt * numpy.mean(self.market_beliefs[time - self.memory:]))
            # print self.belief
            diff = self.belief - self.market_belief

            if diff > self.min_gap_to_trade and position < self.max_position: #believe it's underpriced - buy
                pct_bridge = self.pct_bridge_reflexive if price_dropped else self.pct_bridge
                qty = min(self.max_trade, self.find_qty_buy(check_callback, pct_bridge, self.belief))
                if position + qty > self.max_position: qty = self.max_position - position
                execute_callback('buy', qty)
            elif diff < (-1 * self.min_gap_to_trade) and position > self.min_position: #believe it's overpriced - sell
                pct_bridge = self.pct_bridge_reflexive if not price_dropped else self.pct_bridge
                qty = min(self.max_trade, self.find_qty_sell(check_callback, self.pct_bridge, self.belief))
                if position - qty < self.min_position: qty = - (self.min_position - position)
                execute_callback('sell', qty)

        #print str(time) + ", " + str(cash_callback()) + ", " + str(shares_callback()) + ", "  + str(shares_callback() * market_belief + cash_callback()) + ", " + str(self.belief) + ", " + str(market_belief)

        # #test to see relationship between market belief at t-1 and t 
        # #moves opposite direction 60-70% of the time
        # if time == 100:
        #     f = open('output2.txt', 'a+')
        #     minus_1 = [0] + self.market_beliefs[:99]
        #     plus_1 = self.market_beliefs[1:] + [0]
        #     func = lambda old, new: -1 if new < old else 1 #-1 if dropped, 1 if went up
        #     prev = map(func, minus_1, self.market_beliefs)
        #     next = map(func, self.market_beliefs, plus_1)
        #     comparison = map(lambda old, new: 0 if old == new else 1, prev, next) #1 if move opposite, 0 o/w
        #     f.write(str(sum(comparison)) + ", ")
        #     f.close()


def main():
    bots = [MyBot()]
    bots.extend(other_bots.get_bots(5,2))
    # Plot a single run. Useful for debugging and visualizing your
    # bot's performance. Also prints the bot's final profit, but this
    # will be very noisy.
    #plot_simulation.run(bots, lmsr_b=250)
    
    # Calculate statistics over many runs. Provides the mean and
    # standard deviation of your bot's profit.
    run_experiments.run(bots, simulations=1000, lmsr_b=250)
    #plot_simulation.run(bots, lmsr_b=250)

# Extra parameters to plot_simulation.run:
#   timesteps=100, lmsr_b=150

# Extra parameters to run_experiments.run:
#   timesteps=100, num_processes=2, simulations=2000, lmsr_b=150

# Descriptions of extra parameters:
# timesteps: The number of trading rounds in each simulation.
# lmsr_b: LMSR's B parameter. Higher means prices change less,
#           and the market maker can lose more money.
# num_processes: In general, set this to the number of cores on your
#                  machine to get maximum performance.
# simulations: The number of simulations to run.

if __name__ == '__main__': # If this file is run directly
    main()
