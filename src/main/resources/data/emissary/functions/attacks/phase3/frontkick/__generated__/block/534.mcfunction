#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add m.kill.generic
tag @s add m.killMarker
data modify entity @s data.UUID set from entity @e[tag=emissary,tag=emissarymodel,limit=1] UUID