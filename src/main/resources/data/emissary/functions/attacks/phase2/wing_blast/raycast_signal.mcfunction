#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add RANGE em.value 1
execute if score RANGE em.value matches 100.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add endraycast
execute as @s[tag=!endraycast] run function emissary:attacks/phase2/wing_blast/__generated__/block/351
execute as @s[tag=endraycast] run function emissary:attacks/phase2/wing_blast/__generated__/block/352