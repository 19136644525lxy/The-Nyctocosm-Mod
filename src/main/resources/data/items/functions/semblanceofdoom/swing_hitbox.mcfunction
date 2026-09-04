#built using mc-build (https://github.com/mc-build/mc-build)

particle flame ~ ~ ~ 0 0 0 0.01 1 force
execute if predicate emissary:fireballchance run particle sweep_attack ~ ~ ~ 0 0 0 0 1 force
particle smoke ~ ~ ~ 0 0 0 0 1 force
particle firework ~ ~ ~ 0 0 0 0 1 force
execute positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0] run function items:semblanceofdoom/__generated__/block/70