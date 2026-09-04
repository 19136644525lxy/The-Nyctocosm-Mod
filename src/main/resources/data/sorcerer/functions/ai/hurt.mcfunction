#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Sorcerer ns.health -= @s ns.damagetaken
scoreboard players reset @s ns.damagetaken
execute store result bossbar minecraft:sorcerer value run scoreboard players get Sorcerer ns.health
bossbar set minecraft:sorcerer color white
schedule function sorcerer:ai/__generated__/sequence/1 2t replace
schedule function sorcerer:ai/__generated__/sequence/2 4t replace
schedule function sorcerer:ai/__generated__/sequence/3 6t replace
execute as @e[tag=sorcerermodel] at @s run function sorcerer:ai/__generated__/block/179