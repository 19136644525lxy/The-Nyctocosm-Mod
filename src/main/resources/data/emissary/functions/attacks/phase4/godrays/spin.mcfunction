#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~1 ~
execute as @e[tag=emissary,tag=attack,tag=godrays,tag=!origin] at @s facing entity @a[sort=nearest,gamemode=!spectator,tag=!emissarydebug,distance=..100] feet run tp @s ~ ~ ~ ~ ~
execute at @s rotated ~45 0 positioned ^ ^ ^4 run tp @e[tag=emissary,tag=attack,tag=godrays,tag=1] ~ ~ ~ ~ ~
execute at @s rotated ~135 0 positioned ^ ^ ^4 run tp @e[tag=emissary,tag=attack,tag=godrays,tag=2] ~ ~ ~ ~ ~
execute at @s rotated ~225 0 positioned ^ ^ ^4 run tp @e[tag=emissary,tag=attack,tag=godrays,tag=3] ~ ~ ~ ~ ~
execute at @s rotated ~315 0 positioned ^ ^ ^4 run tp @e[tag=emissary,tag=attack,tag=godrays,tag=4] ~ ~ ~ ~ ~
execute as @e[tag=emissary,tag=attack,tag=godrays,tag=!origin] at @s run function emissary:attacks/phase4/godrays/__generated__/block/675