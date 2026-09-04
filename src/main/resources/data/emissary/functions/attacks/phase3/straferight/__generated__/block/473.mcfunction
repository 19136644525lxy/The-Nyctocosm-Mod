#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ^-0.3 ^ ^ ~ ~
execute at @e[tag=emissary,tag=locator,tag=wing,tag=phase2] run particle cloud ~ ~ ~ 0 0 0 0.05 2 force
execute at @e[tag=emissary,tag=locator,tag=foot] positioned over world_surface run particle block stone ~ ~ ~ 0.1 0.1 0.1 0.05 2 force