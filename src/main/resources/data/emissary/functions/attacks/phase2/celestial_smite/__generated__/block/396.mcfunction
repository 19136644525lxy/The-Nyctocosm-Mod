#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove impact
execute as @a[distance=..15] at @s run function emissary:attacks/phase2/celestial_smite/__generated__/block/397
execute positioned ~ ~1 ~ rotated ~ 0 run function effects:emissary/explosion
fill ~-4 ~-2 ~-4 ~4 ~2 ~4 fire replace air
playsound nyctocosm:emissary.attack.celestial_smite.explosion master @a[distance=..300] ~ ~ ~ 50