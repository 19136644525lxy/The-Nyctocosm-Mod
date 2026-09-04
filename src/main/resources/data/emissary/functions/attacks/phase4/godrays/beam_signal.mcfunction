#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add RANGE em.value 1
execute if score RANGE em.value matches 70.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add endraycast
execute positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[gamemode=!spectator,tag=!emissarydebug,dx=0] run tag @s add endraycast
execute as @s[tag=!endraycast] run function emissary:attacks/phase4/godrays/__generated__/block/681
execute as @s[tag=endraycast] run function emissary:attacks/phase4/godrays/__generated__/block/682