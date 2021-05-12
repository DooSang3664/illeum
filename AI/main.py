from fastapi import FastAPI, Request
from fastapi.openapi.utils import get_openapi

from router import face_router

from fastapi.openapi.docs import get_swagger_ui_html, get_redoc_html

OPEN_API_URL: str = "/api/openapi.json"

app = FastAPI(openapi_url=OPEN_API_URL)

app.include_router(face_router.router, tags=["Face"], prefix="/api/face")

@app.get("/api/docs")
async def get_swagger_documentation():
    return get_swagger_ui_html(openapi_url=OPEN_API_URL, title="docs")


@app.get("/api/redoc")
async def get_redoc_documentation():
    return get_redoc_html(openapi_url=OPEN_API_URL, title="docs")