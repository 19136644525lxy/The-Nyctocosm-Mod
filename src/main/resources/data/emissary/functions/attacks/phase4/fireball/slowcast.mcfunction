#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = fireballdamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase4/fireball/__generated__/block/704
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^1
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add end
execute positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[gamemode=!spectator,tag=!emissarydebug,dx=0] run tag @s add end
execute at @s run particle smoke ~ ~ ~ 0 0 0 0.01 1 force
execute at @s run particle flame ~ ~ ~ 0.4 0.4 0.4 0 3 force
execute at @s run particle wax_on ~ ~ ~ 0.3 0.3 0.3 0 2 force
execute at @s run particle large_smoke ~ ~ ~ 0 0 0 0 1 force
execute at @s run particle lava ~ ~ ~ 0 0 0 0 1 force