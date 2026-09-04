#built using mc-build (https://github.com/mc-build/mc-build)

spreadplayers ~ ~ 10 20 false @s
execute at @s facing entity @a[sort=nearest,limit=1] feet run function sorcerer:summon
execute at @s as @a[distance=..40] at @s run function music:sorcerer/start
particle squid_ink ~ ~2 ~ 0.5 1 0.5 300
kill @s