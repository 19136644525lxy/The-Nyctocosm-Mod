#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add damagetemp
execute at @s as @a[distance=..10,gamemode=survival] run function nyctocosm:mobs/death_walker/__generated__/block/36
tag @s remove damagetemp
execute at @s rotated ~ 0 run particle explosion_emitter ^ ^ ^3 0 0 0 0 1 force
execute at @s rotated ~ 0 run particle block dirt ^ ^ ^3 3 0.5 3 0.2 1000 force
execute at @s run playsound minecraft:entity.generic.explode master @a[distance=..30] ~ ~ ~ 3