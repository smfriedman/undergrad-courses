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
        self.belief = 50
        self.market_belief = 0.5
        self.market_beliefs = []

        self.max_position = 120
        self.min_position = -120
        self.max_trade = 40

        self.memory = 3
        self.pct_bridge = 0.5
        self.pct_info = .2
        self.pct_mkt = .8
        self.min_gap_to_trade = .2
    
    def update_probability(self):
        return

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

    def weightedAverage(self, info):
        weightedSum = 0
        divisor = 0
        if len(info) > 20: # weight last 10 to previous 10 to others 10:3:1
            for i in range(len(info)):
                # print str(i)
                if i >= len(info) - 10:
                    weightedSum += 10 * info[i]
                    divisor += 10
                elif i >= len(info) - 20:
                    weightedSum += 3 * info[i]
                    divisor += 3
                else:
                    weightedSum += info[i]
                    divisor += 1
            weightedAvg = float(weightedSum) / float(divisor)
            return weightedAvg
        elif len(info) > 10: # weight last 10 to others 2:1
            for i in range(len(info)):
                # print str(i)
                if i >= len(info) - 10:
                    weightedSum += 3 * info[i]
                    divisor += 3
                else:
                    weightedSum += info[i]
                    divisor += 1
            weightedAvg = float(weightedSum) / float(divisor)
            return weightedAvg
        else:
            return numpy.mean(info)

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

        # pct_bridge = 0.5
        # if len(self.market_beliefs) > 20:
        #     diff = self.market_beliefs[-2] - self.market_beliefs[-1]
        #     if diff > 0: #price went down last round->underpriced->buy
        #         qty = self.find_qty_buy(check_callback, pct_bridge, self.market_beliefs[-2])
        #         execute_callback('buy', qty)
        #     elif diff < 0: #price went up last round->overpriced->sell
        #         qty = self.find_qty_sell(check_callback, pct_bridge, self.market_beliefs[-2])
        #         execute_callback('sell', qty)

        # print str(cash_callback()) + ", " + str(shares_callback())

        time = len(self.information)
        position = shares_callback()
        if time > self.memory:
            self.belief = (self.pct_info * 100 * self.weightedAverage(self.information) #[time - self.memory:]) 
                + self.pct_mkt * numpy.mean(self.market_beliefs[time - self.memory:]))
            # print self.belief
            diff = self.belief - self.market_belief
            if diff > self.min_gap_to_trade and position < self.max_position: #believe it's underpriced - buy
                qty = min(self.max_trade, self.find_qty_buy(check_callback, self.pct_bridge, self.belief))
                execute_callback('buy', qty)
            elif diff < (-1 * self.min_gap_to_trade) and position > self.min_position: #believe it's overpriced - sell
                qty = min(self.max_trade, self.find_qty_sell(check_callback, self.pct_bridge, self.belief))
                execute_callback('sell', qty)
        # print "    Market Belief: " + str(self.market_belief)
        # print "    Self Belief:   " + str(self.belief)

def main():
    bots = [MyBot()]
    bots.extend(other_bots.get_bots(10,10))#(5,2))
    # Plot a single run. Useful for debugging and visualizing your
    # bot's performance. Also prints the bot's final profit, but this
    # will be very noisy.
   # plot_simulation.run(bots, lmsr_b=250)
    
    # Calculate statistics over many runs. Provides the mean and
    # standard deviation of your bot's profit.
   # run_experiments.run(bots, simulations=2000, lmsr_b=250)
    plot_simulation.run(bots, lmsr_b=250)

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