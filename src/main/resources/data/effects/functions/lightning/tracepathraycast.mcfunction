#built using mc-build (https://github.com/mc-build/mc-build)

particle firework ~ ~ ~ 0 0 0 0 1 force
particle electric_spark ~ ~ ~ 0 0 0 0 1 force
execute if entity @e[tag=nextpoint,distance=..0.12] run tag @s add endraycast
execute as @s[tag=!endraycast] positioned ^ ^ ^0.1 run function effects:lightning/tracepathraycast
execute as @s[tag=endraycast] run function effects:lightning/__generated__/block/654