#built using mc-build (https://github.com/mc-build/mc-build)

summon minecraft:marker ~ ~ ~ {Tags:['new','aj.aj_cast_spell','aj.aj_cast_spell.root']}
execute as @e[type=minecraft:marker,tag=aj.aj_cast_spell.root,tag=new,distance=..1,limit=1] rotated ~ 0 run function aj_cast_spell:summon/__generated__/execute/5
function aj_cast_spell:assert_animation_flags