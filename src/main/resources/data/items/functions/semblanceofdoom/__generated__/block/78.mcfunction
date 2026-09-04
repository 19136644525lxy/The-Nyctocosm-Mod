#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ^ ^ ^1
particle flame ~ ~ ~ 0.4 0.4 0.4 0.02 5 force
particle end_rod ~ ~ ~ 0.2 0.2 0.2 0.01 2 force
particle smoke ~ ~ ~ 0.4 0.4 0.4 0.02 5 force
particle lava ~ ~ ~ 0.4 0.4 0.4 0 2 force
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add end
execute positioned ~-0.5 ~-0.5 ~-0.5 if entity @e[type=#nyctocosm:livingthings,dx=0,tag=!UUIDMatch] run tag @s add end
execute positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0,tag=!UUIDMatch] run function items:semblanceofdoom/__generated__/block/79