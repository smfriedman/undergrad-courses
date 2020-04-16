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
        self.belief = -1
        self.market_belief = -1
        self.market_beliefs = []
    
    def update_probability(self):
        # self.belief = (0.9 * self.belief + 5 * self.information[-1] + 5 * self.information[-2])
        pHat = self.belief
        pastResults = self.information
        if len(pastResults) > 25 and pHat > 0.2 and pHat < 0.8:
            # if pHat < .2:
            #     # do something?
            # elif pHat > .8:
            #     # do something?
            # else: 
            n = 25
            confidence = .9346
            halfLength = confidence * math.sqrt(pHat*(1-pHat)/n)
            lowerBound = pHat - halfLength
            upperBound = pHat + halfLength
            observedMean = numpy.mean(pastResults[len(pastResults)-25:])
            if observedMean < lowerBound or observedMean > upperBound:
                self.belief = observedMean
                return True
        else:
            self.belief = self.market_belief

        return False

    def new_information(self, info, time):
        """Get information about the underlying market value.
        
        info: 1 with probability equal to the current
          underlying market value, and 0 otherwise.
        time: The current timestep for the experiment. It
          matches up with possible_jump_locations. It will
          be between 0 and self.timesteps - 1."""
        self.information.append(info)
        # #sit first 5 rounds out
        # if time <= 25:
        #     self.belief = self.market_belief
        # else:
        #     self.update_probability()

    def trades_history(self, trades, time):
        """A list of everyone's trades, in the following format:
        [(execution_price, 'buy' or 'sell', quantity,
          previous_market_belief), ...]
        Note that this isn't just new trades; it's all of them."""
        self.trades = trades

    def find_qty_buy(check_callback, pct_bridge):

        return

    def find_qty_sell(check_callback, pct_bridge):
        return

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
        self.market_beliefs.append(market_belief);

        if self.update_probability() is True:
            #sell
            if market_belief > self.belief:
                execute_callback('sell', 15)
            #buy
            elif self.belief < market_belief:
                execute_callback('buy', 15)

        # profit: (1800, 2500)
        # if(len(self.market_beliefs) > 2):
        #     if(self.market_beliefs[-2] > self.market_beliefs[-1]):
        #         execute_callback('buy', 15)
        #     else:
        #         execute_callback('sell', 15)

        # profit (2500ish, 6500ish)
        # #sell
        # if market_belief - self.belief > 0.05:
        #     execute_callback('sell', 15)
        # #buy
        # elif self.belief - market_belief > 0.05:
        #     execute_callback('buy', 15)

        # profit garbage
        # # Place a randomly sized trade in the direction of
        # # our last information. What could possibly go wrong?
        # quantity = random.choice(xrange(1, 100))
        # if (self.information[-1] == 1
        #     and check_callback('buy', quantity) < 99.0):
        #     execute_callback('buy', quantity)
        # elif check_callback('sell', quantity) > 1.0:
        #     execute_callback('sell', quantity)

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
