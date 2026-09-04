#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation CurrZigZag e.zigzagpoint = @s e.zigzagpoint
scoreboard players set 1 value 1
scoreboard players operation CurrZigZag e.zigzagpoint += 1 value
execute as @e[tag=lightningsegment] if score @s e.zigzagpoint = CurrZigZag e.zigzagpoint run tag @s add nextpoint
execute as @s at @s facing entity @e[tag=nextpoint,limit=1] feet run tp @s ~ ~ ~ ~ ~
scoreboard players reset CurrZigZag e.zigzagpoint
scoreboard players reset 1 value
execute at @s run function effects:lightning/tracepathraycast
tag @e[tag=nextpoint] remove nextpoint