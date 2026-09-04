#built using mc-build (https://github.com/mc-build/mc-build)

function spells:ring/subtract
execute at @s unless entity @e[tag=spell,tag=origin,tag=plane,distance=..2] run function spells:ring/cast_ring_plane
execute at @s positioned ~ ~1 ~ run function effects:soul_swirl/explode
execute at @s run playsound nyctocosm:spells.soul_release master @a[distance=..10]