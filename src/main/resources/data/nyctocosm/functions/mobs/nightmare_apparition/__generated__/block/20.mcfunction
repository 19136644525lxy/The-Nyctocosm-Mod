#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s facing entity @a[gamemode=survival,distance=2..13,sort=nearest,limit=1] feet run tp @s ^ ^ ^0.05
execute at @s facing entity @e[tag=apparition,distance=0.001..2] feet rotated ~ 0 run tp @s ^ ^ ^-0.05
execute at @s facing entity @a[distance=..2] feet rotated ~ 0 run tp @s ^ ^ ^-0.05