#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset Sorcerer ns.health
scoreboard players set SorcererDying value 1
scoreboard players set Sorcerer ns.attacking 0
execute as @e[tag=sorcererhitbox] at @s run function sorcerer:__generated__/block/183
function sorcerer:ai/die