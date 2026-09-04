#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s facing entity @a[distance=..300,limit=1,gamemode=!spectator,tag=!emissarydebug,sort=nearest] feet rotated ~ 0 run tp @s ^-4 ^ ^ ~ ~
execute at @e[tag=emissary,tag=locator,tag=hand] run particle cloud ~ ~ ~ 0.2 0.2 0.2 0 5 force
execute at @e[tag=emissary,tag=locator,tag=foot] run particle cloud ~ ~ ~ 0.2 0.2 0.2 0 5 force
execute at @e[tag=emissary,tag=locator,tag=wing] run particle cloud ~ ~ ~ 0.2 0.2 0.2 0 5 force