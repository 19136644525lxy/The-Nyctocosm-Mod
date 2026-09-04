#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emissary] run function emissary:entitytick
execute as @a[tag=em.cutscene] run function emissary:cutscene/subtick
function emissary:ai/subtick