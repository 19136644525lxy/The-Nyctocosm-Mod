#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @a[distance=..30,limit=1,gamemode=!spectator,sort=nearest] feet rotated ~ 0 run function sorcerer:attacks/dashstrike/__generated__/block/33
execute as @e[tag=dashtarget,tag=justsummoned] at @s run function sorcerer:attacks/dashstrike/__generated__/block/34