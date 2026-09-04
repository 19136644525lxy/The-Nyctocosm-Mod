#built using mc-build (https://github.com/mc-build/mc-build)

particle dust_color_transition 0.741 0.000 1.000 1.5 0.000 0.569 1.000 ~ ~ ~ 0 0 0 0 1 force
particle enchanted_hit ~ ~ ~ 0 0 0 0 1
particle sweep_attack ~ ~ ~ 0.1 0.1 0.1 0 1
tag @s add damagetemp
execute positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0,tag=!fissurehit] at @s run function items:nullfissure/swinghit
tag @s remove damagetemp