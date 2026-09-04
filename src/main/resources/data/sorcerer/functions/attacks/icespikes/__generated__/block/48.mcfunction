#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.icespikestimer 1
execute if score @s ns.icespikestimer matches 1..5 run function sorcerer:attacks/icespikes/__generated__/block/49
execute as @s[scores={ns.icespikestimer=6..10}] at @s run function sorcerer:attacks/icespikes/__generated__/block/50
execute if score @s ns.icespikestimer matches 20..24 run tp @s ~ ~-0.4 ~
kill @s[scores={ns.icespikestimer=25..}]