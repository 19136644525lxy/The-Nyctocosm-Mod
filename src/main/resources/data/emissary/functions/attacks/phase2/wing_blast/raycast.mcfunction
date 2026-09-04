#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add RANGE em.value 1
execute if score RANGE em.value matches 100.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add endraycast
execute positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[gamemode=!spectator,tag=!emissarydebug,dx=0] run tag @s add endraycast
execute as @s[tag=!endraycast] run function emissary:attacks/phase2/wing_blast/__generated__/block/353
execute as @s[tag=endraycast] run function emissary:attacks/phase2/wing_blast/__generated__/block/381