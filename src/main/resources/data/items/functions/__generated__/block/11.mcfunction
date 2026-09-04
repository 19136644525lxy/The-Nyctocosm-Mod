#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=doomshoothit] at @s run function items:__generated__/block/12
execute at @s run particle explosion ~ ~ ~ 0.3 0.3 0.3 0 2 force
execute at @s run playsound minecraft:entity.generic.explode master @a[distance=..50] ~ ~ ~ 2
execute at @s run fill ~-1 ~-1 ~-1 ~1 ~1 ~1 fire replace air
kill @s