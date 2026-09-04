#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add aj.aj_cast_spell.anim.cast_spell
execute if score .aj.anim_loop aj.aj_cast_spell.animating matches 0 run function aj_cast_spell:animation_loop
scoreboard players set #execute LANG_MC_INTERNAL 1