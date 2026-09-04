#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add emissary
tag @s add attack
tag @s add spell
tag @s add divine_blast
tag @s add shooter
tp @s ~ ~ ~ ~ ~
execute at @s facing entity @a[sort=nearest,limit=1,distance=..100,gamemode=!spectator,tag=!emissarydebug] feet run tp @s ~ ~ ~ ~ ~