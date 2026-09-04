#built using mc-build (https://github.com/mc-build/mc-build)

data modify entity @s Rotation[1] set from entity @e[tag=emissary,tag=attack,tag=origin,tag=godrays,limit=1] Rotation[1]
execute at @s run function emissary:attacks/phase4/godrays/beam