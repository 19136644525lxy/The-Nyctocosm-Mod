#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s em.health run data get entity @s Health 1
scoreboard players set 0 temp 1000
scoreboard players operation 0 temp -= @s em.health
scoreboard players operation @s em.health = 0 temp
scoreboard players operation Emissary em.health -= @s em.health
data merge entity @s {Health:1000f}
function emissary:ai/hitbox/hurt