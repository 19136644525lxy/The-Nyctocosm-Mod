#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score HurtTime em.value run data get entity @s HurtTime
execute as @s unless score HurtTime em.value matches 5.. run function emissary:ai/phase/4/__generated__/block/780
execute positioned over world_surface run particle lava ~ ~ ~ 0.3 0.3 0.3 0 1 force
execute positioned over world_surface run particle flame ~ ~ ~ 0.3 0.3 0.3 0.05 3 force
execute positioned over world_surface run particle smoke ~ ~ ~ 0.3 0.3 0.3 0.05 3 force
execute positioned over world_surface run particle block stone ~ ~ ~ 0.6 0.6 0.6 0.05 6 force
execute positioned over world_surface run particle block dirt ~ ~ ~ 0.6 0.6 0.6 0.05 6 force