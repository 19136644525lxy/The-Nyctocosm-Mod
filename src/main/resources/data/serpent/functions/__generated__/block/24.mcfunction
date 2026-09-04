#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s s.beamDelay 1
kill @s[scores={s.beamDelay=..0}]
tag @s add damagetemp
execute as @s[scores={s.beamDelay=..60}] rotated 0 -90 run function serpent:attack/beam/raycast
tag @e remove damagetemp
execute as @s[scores={s.beamDelay=80}] run function serpent:__generated__/block/25
execute as @s[scores={s.beamDelay=81..139}] run function serpent:__generated__/block/26
execute as @s[scores={s.beamDelay=140}] at @a[tag=serpenttarget,limit=1] run function serpent:__generated__/block/27