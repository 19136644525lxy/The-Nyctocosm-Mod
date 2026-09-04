#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.icespikestimer 1
tp @s ^ ^ ^0.5
scoreboard players remove @s value 1
execute as @s[scores={value=..0}] run function sorcerer:attacks/icespikes/__generated__/block/52
execute as @s[scores={ns.icespikestimer=25..}] at @s run function sorcerer:attacks/icespikes/__generated__/block/53
particle block ice ~ ~ ~ 0.1 0.1 0.1 1 5