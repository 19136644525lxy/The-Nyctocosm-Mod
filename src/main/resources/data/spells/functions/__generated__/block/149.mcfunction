#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=lightningdraw,tag=Fulminance,distance=..20] facing entity @s feet run function effects:lightning/draw_spark
tag @s add hit