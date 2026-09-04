#built using mc-build (https://github.com/mc-build/mc-build)

tp @s[tag=pos] ~ ~ ~ ~1 ~
tp @s[tag=neg] ~ ~ ~ ~-1 ~
tp @s[tag=up] ~ ~ ~ ~1.5 ~
tp @s[tag=down] ~ ~ ~ ~-1.5 ~
execute as @s[tag=!converge] run function crafting:__generated__/block/15
execute as @s[tag=converge] run function crafting:__generated__/block/16
execute store result entity @s Pose.Head[0] float 0.1 run scoreboard players get @s daisringtimer