#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @a[sort=nearest,gamemode=!spectator,tag=!emissarydebug,distance=..100] feet run tp @s ~ ~ ~ ~ ~
tag @s add emissary
tag @s add attack
tag @s add godrays
tag @s add origin
tag @s add justsummoned