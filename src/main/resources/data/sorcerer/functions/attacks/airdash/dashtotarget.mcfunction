#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @e[tag=dashtarget] feet run tp @s ^ ^ ^1.5
execute if entity @e[tag=dashtarget,distance=..2] run scoreboard players set @s aj.frame 39
execute positioned ~ ~1 ~ run particle dust 0.741 0.000 1.000 1.5 ~ ~ ~ 1 1 1 0 15 force
execute positioned ~ ~1 ~ run particle dust 0.0 0.000 0.000 1.5 ~ ~ ~ 1 1 1 0 20 force