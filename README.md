# Step Height Modification Mod
Allows you to walk up <x> blocks as if they are stairs.
Uses client commands for configuration.  
No ModMenu integration planned. I don't like having to go through 9 million menus just to get to one setting. Trust me, typing will be faster.  

## Commands
1. `/stepup`: Provides information
1. `/stepup help`: Provides list of commands with description
1. `/stepup height <float>`: Sets the height to climb
    * Min 0, Max: infinity, decimals allowed
1. `/stepup sprintOnly <boolean>`: Sets whether climbing should only be done while sprinting
    * Default is true because walking up random blocks while trying to be precise is annoying as hecc
1. `/stepup reset`: Resets the step height
    * The vanilla value is 0.6f.