#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove s.casting
function spells:__generated__/block/191
scoreboard players reset @s m.spell.cast_time
function effects:shockwave/spellplane
scoreboard players set @e[tag=plane,tag=mobile,tag=origin,limit=1,sort=nearest,distance=..0.2] s.duration 0