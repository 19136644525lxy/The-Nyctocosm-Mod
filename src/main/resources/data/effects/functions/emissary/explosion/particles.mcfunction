#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=locator,tag=explosionpoint,distance=..20] run particle explosion ~ ~ ~ 2 2 2 0 5 force
particle flash ~ ~ ~ 6 6 6 0 20 force
execute at @e[tag=locator,tag=explosionpoint,distance=..20] run particle flame ~ ~ ~ 2 2 2 0.05 30 force
execute at @e[tag=locator,tag=explosionpoint,distance=..20] run particle lava ~ ~ ~ 2 2 2 0.05 6 force
execute at @e[tag=locator,tag=explosionpoint,distance=..20] run particle large_smoke ~ ~ ~ 2 2 2 0.05 6 force
execute at @e[tag=locator,tag=explosionpoint,distance=..20] run particle campfire_cosy_smoke ~ ~ ~ 2 2 2 0.05 6 force
execute at @e[tag=locator,tag=explosionpoint,limit=2,sort=random,distance=..20] run function effects:lightning/random_single