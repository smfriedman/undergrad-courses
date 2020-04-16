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
        self.last_jump = 0
    
    def update_probability(self, time):
        average = numpy.mean(self.information[self.last_jump:])

        # 90%
        threshold_z = 1.645

        if time <= 15:
            self.belief = (0.85 * self.belief + 15 * self.information[-1])
        elif time <= 35:
            self.belief = average
        else:
            if time - self.last_jump >= 40: 
                old_avg = numpy.mean(self.information[self.last_jump:time-26])
                new_avg = numpy.mean(self.information[time-25:])

                #0 or 1 will cause division by 0 below
                if old_avg == 0:
                    old_avg += 0.001
                if old_avg == 1:
                    old_avg -= 0.001
                if new_avg == 0:
                    new_avg += 0.001
                if new_avg == 1:
                    new_avg -= 0.001

                # 2 proportion Z test for pooled p1, p2 (relaxed assumptions...):
                p_hat = average
                z = (new_avg - old_avg) / (p_hat * (1 - p_hat) * math.sqrt((1.0 / (time - self.last_jump - 25)) + (1.0 / 25)))

                #are we 90% confident a jump occured 25 rounds ago?
                if abs(z) > threshold_z:
                    # print "change"
                    # print time
                    # print self.belief
                    self.belief = new_avg
                    self.last_jump = time - 25
                    # print z
                    # print self.belief
                else:
                    self.belief = average
            else:
                self.belief = average    


    def new_information(self, info, time):
        """Get information about the underlying market value.
        
        info: 1 with probability equal to the current
          underlying market value, and 0 otherwise.
        time: The current timestep for the experiment. It
          matches up with possible_jump_locations. It will
          be between 0 and self.timesteps - 1."""
        self.information.append(info)
        self.update_probability(time)

    def trades_history(self, trades, time):
        """A list of everyone's trades, in the following format:
        [(execution_price, 'buy' or 'sell', quantity,
          previous_market_belief), ...]
        Note that this isn't just new trades; it's all of them."""
        self.trades = trades

    def find_qty_buy(check_callback, pct_bridge, confidence):
        return

    def find_qty_sell(check_callback, pct_bridge, confidence1):
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

        #sell
        if market_belief > self.belief:
            execute_callback('sell', 15)
        #buy
        elif self.belief < market_belief:
            execute_callback('buy', 15)

        #profit: (1800, 2500)
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
#    plot_simulation.run(bots, lmsr_b=250)
    
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
