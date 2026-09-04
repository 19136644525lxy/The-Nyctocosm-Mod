#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Nullpoint ns.health -= @s ns.damagetaken
scoreboard players reset @s ns.damagetaken
execute as @e[tag=aj.ajnullpoint.root,tag=!aj.ajnullpoint.animation.emit] at @s run function sorcerer:ai/__generated__/block/180
execute as @e[tag=sorcererstaffmodel] at @s run function sorcerer:ai/__generated__/block/181