#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score TEMP ns.health run data get entity @s Health
scoreboard players operation 0 temp = @s ns.health
scoreboard players operation 0 temp -= TEMP ns.health
scoreboard players operation @s ns.damagetaken = 0 temp
function sorcerer:ai/hurt
scoreboard players reset @s ns.damagetaken
scoreboard players reset TEMP ns.health
data merge entity @s {Health:1024f}