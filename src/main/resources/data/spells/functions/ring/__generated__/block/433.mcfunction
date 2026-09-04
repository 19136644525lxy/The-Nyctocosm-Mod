#built using mc-build (https://github.com/mc-build/mc-build)

execute align xz positioned ~0.5 ~0.1 ~0.5 run summon marker ~ ~ ~ {Tags:["spell","origin","ring","plane","justsummoned"]}
data modify entity @e[tag=justsummoned,tag=ring,tag=plane,limit=1] data.UUID set from entity @s UUID
execute as @e[tag=justsummoned,tag=ring,tag=plane] run function spells:ring/__generated__/block/434
execute at @e[tag=spell,tag=origin,distance=..2,sort=nearest,limit=1] rotated ~ 0 positioned ~ ~0.2 ~ run function effects:shockwave/spellplane
execute at @s run playsound nyctocosm:spells.cast.magic_plane master @a[distance=..10]
execute at @s align xz positioned ~0.5 ~0.1 ~0.5 run function spells:ring/__generated__/block/435