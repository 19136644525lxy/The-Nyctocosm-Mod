#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add damagetemp
particle explosion ~ ~ ~ 1 1 1 0 20 force
particle flame ~ ~ ~ 0 0 0 0.3 20 force
particle lava ~ ~ ~ 0.4 0.4 0.4 0.3 20 force
playsound minecraft:entity.generic.explode master @a[distance=..20] ~ ~ ~
function magic:calcstatsspell
execute as @e[type=#nyctocosm:livingthings,distance=..6,tag=!damagetemp] at @s run function artifacts:expulsion/__generated__/block/32
tag @s remove damagetemp