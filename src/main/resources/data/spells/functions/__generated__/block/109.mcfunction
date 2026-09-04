#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s run particle minecraft:flame ~ ~ ~ 0.2 0.2 0.2 0 10 force
execute at @s run particle minecraft:end_rod ~ ~ ~ 0.1 0.1 0.1 0 5 force
execute at @s run particle minecraft:smoke ~ ~ ~ 0.1 0.1 0.1 0 10 force
execute at @s run particle minecraft:lava ~ ~ ~ 0.1 0.1 0.1 0 5 force
particle flame ^ ^ ^1 0 0 0 0 1 force
particle flame ^ ^ ^-1 0 0 0 0 1 force
particle flame ^ ^1 ^ 0 0 0 0 1 force
particle flame ^ ^-1 ^ 0 0 0 0 1 force
execute as @s[x_rotation=-90..-88] run function spells:__generated__/block/110
execute as @s[x_rotation=88..90] run function spells:__generated__/block/111
execute at @s run tp @s[tag=!flipped] ~ ~ ~ ~ ~-15
execute at @s run tp @s[tag=flipped] ~ ~ ~ ~ ~15