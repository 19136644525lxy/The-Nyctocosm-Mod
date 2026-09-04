#built using mc-build (https://github.com/mc-build/mc-build)

data merge entity @s {Tags:["emissary","incurrer"]}
spreadplayers ~ ~ 20 30 false @s
execute at @s facing entity @a[sort=nearest,distance=..50,limit=1] feet rotated ~ 0 run function emissary:spawn
kill @s