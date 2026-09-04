#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s align xz positioned ~0.5 ~0.1 ~0.5 run summon marker ~ ~ ~ {Tags:["spell","origin","plane"]}
execute at @s run playsound nyctocosm:spells.cast.magic_plane master @a[distance=..10]
execute at @e[tag=spell,tag=origin,distance=..2,sort=nearest,limit=1] rotated ~ 0 positioned ~ ~0.2 ~ run function effects:shockwave/spellplane
execute at @s align xz positioned ~0.5 ~0.1 ~0.5 run function spells:__generated__/block/194