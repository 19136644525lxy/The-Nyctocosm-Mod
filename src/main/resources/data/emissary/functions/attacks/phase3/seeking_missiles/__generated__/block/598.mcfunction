#built using mc-build (https://github.com/mc-build/mc-build)

playsound nyctocosm:emissary.attack.seeking_missiles.impact master @a[distance=..100] ~ ~ ~ 5
particle explosion ~ ~ ~ 0 0 0 0 1 force
scoreboard players operation @s ncd_damage = seekingmissilesdamage em.value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
execute positioned ~-0.5 ~-0.5 ~-0.5 run function emissary:attacks/phase3/seeking_missiles/__generated__/block/599