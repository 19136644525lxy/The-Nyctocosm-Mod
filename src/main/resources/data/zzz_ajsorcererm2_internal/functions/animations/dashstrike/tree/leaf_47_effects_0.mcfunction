execute facing entity @e[tag=dashtarget,limit=1] feet run tp @s ^ ^ ^1.5
execute at @e[tag=aj.ajsorcererm2.bone.group] run particle dust 0.741 0.000 1.000 1 ~ ~1.6 ~ 0.1 0.1 0.1 0 5 force
particle dust 0.000 0.569 1.000 2 ~ ~2 ~ 0.5 1 0.5 0 20 force
execute as @a[distance=..1.5] run function sorcerer:attacks/dashstrike/hit