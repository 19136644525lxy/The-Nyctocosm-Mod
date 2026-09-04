#built using mc-build (https://github.com/mc-build/mc-build)

advancement revoke @s only items:detection/kill_with_scythe
execute at @s positioned ~ ~1 ~ rotated ~ 0 positioned ^ ^ ^2 run summon marker ~ ~ ~ {Tags:["soul"]}
execute at @s run playsound nyctocosm:spells.soul_reap master @a[distance=..10] ~ ~ ~ 4