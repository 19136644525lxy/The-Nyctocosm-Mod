#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=attack,tag=emissary,tag=projectile,tag=seeking_missiles,tag=!activated] run function emissary:attacks/phase3/seeking_missiles/__generated__/block/610
playsound nyctocosm:emissary.attack.seeking_missiles.activate master @a[distance=..100] ~ ~ ~ 5