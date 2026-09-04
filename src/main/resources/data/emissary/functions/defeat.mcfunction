#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset Emissary em.health
scoreboard players set EmissaryDying em.value 1
scoreboard players set Attacking em.value 0
execute as @e[tag=sorcererhitbox] at @s run function emissary:__generated__/block/30
function emissary:die