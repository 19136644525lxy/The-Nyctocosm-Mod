#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~ ~1.5 ~ positioned ^ ^ ^2 run particle dust 0 0 0 2 ~ ~ ~ 0.7 0.7 0.7 0 30 force
function serpent:headai
tag @s add damagetemp
execute at @s as @a[distance=..2] run function serpent:__generated__/block/8
execute at @s as @a[distance=2..2.5] run function serpent:__generated__/block/15
tag @s remove damagetemp
execute at @s unless entity @a[distance=..500] run function serpent:remove